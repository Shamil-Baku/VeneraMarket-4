/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qarisiqmallar.veneramarket;

import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Tacir Aliyev
 */
public class RandomCodeGenerator {

    public static void main(String[] args) {
        rondomNumbersForBarcode();
    }

    public static String randomCodeGenerate() {

        String randomCode = null;

        for (int i = 0; i < 4; i++) {
            String rand = RandomStringUtils.random(4, true, false);

            if (randomCode == null) {
                randomCode = rand;
            } else {
                randomCode = randomCode + "-" + rand;

            }

        }
        System.out.println(randomCode);
        return randomCode;
    }
    
     public static String rondomNumbersForBarcode(){
        
        String randomCode = null;

        for (int i = 0; i < 5; i++) {
            String rand = RandomStringUtils.random(1, false, true);

            if (randomCode == null) {
                randomCode = rand;
            } else {
                randomCode = randomCode + rand;

            }

        }
        System.out.println(randomCode);
        
        return randomCode;
        
        
    }
    
    
}
