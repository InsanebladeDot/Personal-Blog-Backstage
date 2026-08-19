package com.example.springboottext.untill;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA-2048 OAEP(SHA-256) 传输加密服务
 * <p>
 * 目标:密码在网络上不出现明文。
 * 前端:Web Crypto 使用 RSA-OAEP(hash=SHA-256) 公钥加密密码 -> Base64 上传;
 * 后端:本服务用私钥解密还原明文 -> 再走原有 SHA256Util 加盐校验,存储/校验逻辑不变。
 * <p>
 * 密钥对在首次启动时生成并持久化到磁盘(默认 ./rsa-keys/),重启复用不换钥,
 * 避免前端缓存公钥(24h)后因密钥轮换导致解密失败。
 */
@Slf4j
@Component
public class RSAKeyService {

    @Value("${rsa.key-path:./rsa-keys}")
    private String keyPath;

    private PrivateKey privateKey;
    private String publicKeyBase64;

    @PostConstruct
    public void init() {
        try {
            Path dir = Paths.get(keyPath);
            Path privateFile = dir.resolve("rsa-private.key");
            Path publicFile = dir.resolve("rsa-public.key");

            if (Files.exists(privateFile) && Files.exists(publicFile)) {
                // 1.已有持久化的密钥对:直接加载,保证重启不换钥
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                privateKey = keyFactory.generatePrivate(
                        new PKCS8EncodedKeySpec(Base64.getDecoder().decode(Files.readString(privateFile).trim())));
                PublicKey publicKey = keyFactory.generatePublic(
                        new X509EncodedKeySpec(Base64.getDecoder().decode(Files.readString(publicFile).trim())));
                publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            } else {
                // 2.首次运行:生成 RSA-2048 密钥对并持久化
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
                generator.initialize(2048);
                KeyPair keyPair = generator.generateKeyPair();
                privateKey = keyPair.getPrivate();
                publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

                Files.createDirectories(dir);
                Files.writeString(privateFile,
                        Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
                Files.writeString(publicFile, publicKeyBase64);
            }
            log.info("RSA 密钥初始化完成,密钥目录: {}, 公钥长度: {}", keyPath, publicKeyBase64.length());
        } catch (Exception e) {
            log.error("RSA 密钥初始化失败", e);
            throw new IllegalStateException("RSA 密钥初始化失败", e);
        }
    }

    /**
     * 下发公钥(Base64 编码的 X.509 SPKI),供前端加密密码
     */
    public String getPublicKeyBase64() {
        return publicKeyBase64;
    }

    /**
     * 解密前端 RSA-OAEP(SHA-256 / MGF1-SHA-256) 密文,返回明文密码
     * <p>
     * 注意:必须显式指定 MGF1 摘要为 SHA-256(默认是 SHA-1),
     * 否则与前端 Web Crypto 的 RSA-OAEP+SHA-256 不互通,会抛 BadPaddingException。
     *
     * @param cipherBase64 Base64 编码的密文
     * @return 明文密码
     */
    public String decrypt(String cipherBase64) throws Exception {
        if (cipherBase64 == null || cipherBase64.isEmpty()) {
            throw new IllegalArgumentException("密码密文不能为空");
        }
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        OAEPParameterSpec spec = new OAEPParameterSpec(
                "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.DECRYPT_MODE, privateKey, spec);
        byte[] plain = cipher.doFinal(Base64.getDecoder().decode(cipherBase64));
        return new String(plain, StandardCharsets.UTF_8);
    }
}
