package com.example.lostandfound.utils;

import java.security.SecureRandom;

public class RandomUtils {
     private static final SecureRandom SECURE_RANDOM = new SecureRandom();

     public static byte[] randomBytes(int length){
          byte[] bytes  = new byte[length];
          SECURE_RANDOM.nextBytes(bytes);
          return bytes;
     }

     public static Integer randomInteger(){
          return SECURE_RANDOM.nextInt();
     }

}
