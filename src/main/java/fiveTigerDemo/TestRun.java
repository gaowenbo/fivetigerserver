package fiveTigerDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.qici.fivetiger.GameLogic;
import com.qici.fivetiger.Step;

public class TestRun {

	public static void main(String[] args) throws IOException, ParseException {
		GameLogic gm = new GameLogic();
		gm.go(new Step("1s11"));
		gm.go(new Step("1s21"));
		gm.go(new Step("1s31"));
		System.out.println(gm.toString());
	}

}
