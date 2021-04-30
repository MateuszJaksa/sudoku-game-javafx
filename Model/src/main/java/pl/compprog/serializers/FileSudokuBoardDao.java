package pl.compprog.serializers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import pl.compprog.SudokuBoard;
import pl.compprog.exceptions.InputOutputException;
import pl.compprog.exceptions.NoClassException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws InputOutputException, NoClassException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (SudokuBoard) ois.readObject();
        } catch (IOException e) {
            throw new InputOutputException(e);
        } catch (ClassNotFoundException e) {
            throw new NoClassException(e);
        }
    }

    @Override
    public void write(SudokuBoard obj) throws InputOutputException {
        try (ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            throw new InputOutputException(e);
        }
    }

    @Override
    public void close() {
        System.out.println("Closing resource.");
    }

    @Override
    public void finalize() {
        File file = new File(fileName);
        boolean fileIsNotLocked = file.renameTo(file);
        if (!fileIsNotLocked) {
            System.out.println("File is still locked!");
        }
    }
}
