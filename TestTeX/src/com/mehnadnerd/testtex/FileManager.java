package com.mehnadnerd.testtex;

import com.mehnadnerd.testtex.data.exam.Exam;

import java.io.*;

/**
 * Created by mehnadnerd on 2016-05-02.
 */
public class FileManager {
    public static Exam read(File f) {
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Exam) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(Exam exam, File f) {
        try {
            FileOutputStream fos = new FileOutputStream(f, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(exam);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean export(Exam exam, File f) {
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(exam.toTeXFormat());
            System.out.println("Writing to " + f.toString() + "\n" + exam.toTeXFormat());
            fw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
