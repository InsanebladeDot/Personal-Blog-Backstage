package com.example.springboottext.untill.CosConfig;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class COSUtilTest {

    @Test
    void deleteFile() {
        COSUtil.deleteFile("DataSet/Images/20240810224427429.jpg");
    }
}