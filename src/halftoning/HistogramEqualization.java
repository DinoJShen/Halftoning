package ImageProcessing;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student
 */
public class HistogramEqualization {
    static int rangeCount[] = new int [256];
    static int newGreylvl[] = new int [256];
    static ArrayList<Integer> finalList = new ArrayList<>();
    public static void main(String[] args){
        String fileName = "lena_gray.raw";
        try{
            FileInputStream myInputFile = new FileInputStream(fileName);
            int value;
            String formatter = "%-15s %-15s %-15s %-15s %-15s";
            while ((value = myInputFile.read()) != -1){
                rangeCount[value]++;
            }
            System.out.println(String.format(formatter,"Grey-level","No of Pixel","Run Sum","Normalized","Mutiply"));
            System.out.println("-------------------------------------------------------------------------");
            int cumulativeSum = 0;
            int sum = IntStream.of(rangeCount).sum();
            float normalizedInt;
            for (int i = 0 ; i < rangeCount.length;i++){
                cumulativeSum += rangeCount[i];
                System.out.println(String.format(formatter, i,rangeCount[i],cumulativeSum,normalizedInt= (float)cumulativeSum/sum,newGreylvl[i]=Math.round(normalizedInt*rangeCount.length)));
            }
            myInputFile.close();
        }catch (IOException ex){
            System.out.print("File Error!");
        }
        //read again to replace
        try(FileInputStream myInputFile = new FileInputStream(fileName);){
            int value;
            while ((value = myInputFile.read()) != -1){
                finalList.add(newGreylvl[value]);
            }
            myInputFile.close();
        }catch (IOException ex){
            System.out.print("File Error!");
        }
        
        String fileNameOutput = "Enhance_" + fileName;
        try (FileOutputStream myOutputFile = new FileOutputStream(fileNameOutput)) {
            for (Integer str : finalList) {
                if (str>255){
                    myOutputFile.write(255);
                }
                else if(str < 0) {
                    myOutputFile.write(0);
                }else myOutputFile.write(str);
            }
            myOutputFile.close();
        } catch (IOException ex) {
            System.out.print("File output error!");
        }
    }
}