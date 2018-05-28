package com.aizong.movies;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AService {
    final static String[] REPLACEMENTS = {"\\?","!","\\..*",",","<i>","</i>","\"","'","<"};
    final static String REPLACEMENT_PATTERN = createReplacementPattern();
    public static void main(String[] args) {
	
	String path = "/home/ubility/Documents/toufic/films/New/The Nut Job/The Nut Job (2014)/Nut Job The.Bluray.YIFY.en.srt";

	try (Stream<String> stream = new BufferedReader(new InputStreamReader(new FileInputStream(path))).lines();) {
	    Set<String> lines = stream.collect(Collectors.toSet());

	    lines = lines.stream().filter(e -> !e.matches("^\\d.*")).collect(Collectors.toSet());

	    Set<String[]> collect = lines.stream().map(e -> e.split("\\s")).collect(Collectors.toSet());

	    Map<String, List<String>> words = new HashMap<>();
	    for (String[] data : collect) {

		for (String v : data) {
		    if (v.trim().isEmpty()) {
			continue;
		    }

		    v = v.toLowerCase().trim();

		  v = v.replaceAll("^"+REPLACEMENT_PATTERN, "");
		   
		   v = v.replaceAll(REPLACEMENT_PATTERN+"$", "");

		   // v = Stopwords.stemString(v).toLowerCase();
		    
		    if (Stopwords.isStopword(v.toLowerCase())) {
			continue;
		    }

		    List<String> list = words.get(v);
		    if (list == null) {
			list = new ArrayList<>();
			words.put(v, list);
		    }
		    list.addAll(Arrays.asList(String.join(" ", data)));
		}
	    }

	    List<String> list = new ArrayList<>(words.keySet());
	    Collections.sort(list);
	    for (String word : list) {
		System.out.println(word);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }
    
    public static String createReplacementPattern() {
	String s= "(";
	s+=String.join("|", REPLACEMENTS);
	s+=")";
	return s;
    }
}
