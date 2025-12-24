package com.epam.rd.autotasks.words;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class StringUtil {

    private static boolean checkArr(String[] set) {
        for (String s : set) {
            if (s == null)
                return (false);
        }
        return (true);
    }

    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if (sample == null || words == null || sample.isEmpty() || !checkArr(words))
            return (0);
        int count = 0;
        for (String str : words) {
            if (str.trim().equalsIgnoreCase(sample.trim()))
                ++count;
        }
        return (count);
    }

    public static String[] splitWords(String text) {
        if (text == null || text.isEmpty())
            return (null);

        String[] tmp = text.split("[,.;: ?!]+");
        if (tmp.length == 0)
            return (null);

        int i = 0;
        for (String s : tmp) {
            if (!s.isEmpty())
                ++i;
        }
        String[] result = new String[i];
        i = 0;
        for (String s : tmp) {
            if (!s.isEmpty())
                result[i++] = s;
        }
        return (result);
    }

    private static String identifyPath(String path) {
        boolean hasUnixSep = path.contains("/");
        boolean hasWinSep = path.contains("\\");

        if ((hasUnixSep && hasWinSep) ||
            (path.contains("//") || path.contains("\\\\")))
            return ("INVALID");

        if (path.contains("~")) {
            if (!path.startsWith("~") || path.indexOf("~", 1) != -1)
                return ("INVALID");
            if (hasWinSep || path.matches(".*[a-zA-Z]:.*"))
                return ("INVALID");
            return ("UNIX");
        }

        Pattern driveLetterPattern = Pattern.compile("[a-zA-Z]:");
        Matcher matcher = driveLetterPattern.matcher(path);
        if (matcher.find()) {
            if ((matcher.start() != 0 || matcher.find()) || (hasUnixSep))
                return ("INVALID");
            if (hasWinSep)
                return ("WINDOWS");
        }
        if (hasWinSep)
            return ("WINDOWS");
        if (hasUnixSep)
            return ("UNIX");
        return ("RELATIVE");
    }

    public static String convertPath(String path, boolean toWin) {
        if (path == null || path.isEmpty())
            return (null);
        //PathTypes = {"UNIX", "WINDOWS", "RELATIVE", "INVALID"};
        String pathType = identifyPath(path);
        if (pathType.equals("INVALID"))
            return (null);
        if ((pathType.equals("WINDOWS") && toWin) ||
            (pathType.equals("UNIX") && !toWin) ||
            (pathType.equals("RELATIVE")))
            return (path);

        String res = path;
        if (toWin) {
            if(res.startsWith("~/"))
                res = "C:\\User\\" + res.substring(2);
            else if (res.equals("~"))
                return ("C:\\User");
            else if (res.startsWith("/")) {
                if (res.equals("/"))
                    return ("C:\\");
                res = "C:\\" + res.substring(1);
            }
            return(res.replace('/', '\\'));

        } else {
            String tmp;
            if (path.matches("(?i)^C:\\\\User\\\\.*")) {
                tmp = path.substring(7);
                return ("~" + tmp.replace("\\", "/"));
            }
            if (path.equals("C:\\User"))
                return ("~");

            if (path.matches("^[a-zA-Z]:\\\\?$"))
                return ("/");
            if (path.matches("^[a-zA-Z]:\\\\.*")) {
                tmp = "/" + path.substring(3);
                return (tmp.replace("\\", "/"));
            }
            return (path.replace("\\", "/"));
        }
    }

    public static String joinWords(String[] words) {
        if (words == null || words.length == 0)
            return (null);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String s : words) {
            if (s == null || s.isEmpty())
                continue;
            sb.append(s).append(", ");
        }
        if (sb.length() == 1)
            return (null);
        if (sb.length() > 1)
            sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return (sb.toString());
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}