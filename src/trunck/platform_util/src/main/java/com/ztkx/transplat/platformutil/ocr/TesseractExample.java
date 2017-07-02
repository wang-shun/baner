package com.ztkx.transplat.platformutil.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class TesseractExample {

    public static void main(String[] args) {
        File imageFile = new File("D:\\orc\\te.jpg");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
//        instance.setDatapath("D:\\softwear\\安装包\\ocr\\tess4j-code-200-Tess4J_3-trunk\\tessdata\\");
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}