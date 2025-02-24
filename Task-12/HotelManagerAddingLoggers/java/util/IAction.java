package util;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface IAction {
    void execute() throws ParseException, IOException, CsvValidationException, SQLException;
}
