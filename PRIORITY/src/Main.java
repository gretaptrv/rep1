import textProcessingApp.attempt;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

            List<String> linesInFile = new ArrayList<>();
            List<String> path = new ArrayList<>();
            new attempt.GetPathToFile(path);
            File file = readFile(linesInFile, path);
            if (file == null) {
                System.out.println("Unsuccessful.");
                return;
            }

            int choice = getDirections();
            PrintStream struma = new PrintStream(new FileOutputStream(file));
            switch (choice) {
                case 1:
                    System.out.println(switchLines(linesInFile, struma));
                    break;
                case 2:
                    try {
                        System.out.println(switchTwoWords(linesInFile, struma));
                    } catch (IndexOutOfBoundsException ff) {
                        System.out.println(ff.getMessage());
                    }
                    break;
                case 3:
                    return;
            }
        printFromFileToTextArea(file);

    }

    private static String switchTwoWords(List<String> txtLines, PrintStream writer) {
        int[] indexes = new int[4];
        attempt.wordIndexer ss = new attempt.wordIndexer(indexes);
        if (indexCheck(indexes, txtLines)) {
            int firstWordLineIndex = indexes[0];
            int firstWordIndex = indexes[1];
            int secondWordLineIndex = indexes[2];
            int secondWordIndex = indexes[3];
            swapAB(firstWordIndex, firstWordLineIndex, secondWordIndex, secondWordLineIndex, txtLines);
            printToFile(txtLines, writer);
            return String.format("Successfully switched the words%n");
        } else {
            throw new IndexOutOfBoundsException(String.format("Index out of bounds: %s",
                    Arrays.stream(indexes).mapToObj(String::valueOf).collect(Collectors.joining(", "))));
        }
    }

    private static int getDirections() {
        List<Integer> answer = new ArrayList<>();
        attempt.OpChooser ops = new attempt.OpChooser(answer);
//        System.out.printf("To switch two entire lines enter 1%n" +
//                "To switch two words from different lines enter 2%n" +
//                "To exit this program enter 3%n");
        return answer.get(0);
    }

    private static String switchLines(List<String> linesInFile, PrintStream writer) {
        String result = "";
        int[] lineIndexes = new int[4];
        attempt.lineIndexer ss = new attempt.lineIndexer(lineIndexes);
//        System.out.printf("Enter below two lines, separated by a space, counting from 1:%n");
//        int[] lineIndexes = Arrays.stream(scan.nextLine().split(" "))
//                .mapToInt(Integer::parseInt)
//                .map(e -> e - 1)
//                .toArray();
        if (indexCheck(lineIndexes, linesInFile)) {
            Collections.swap(linesInFile, lineIndexes[0], lineIndexes[1]);
            result =  String.format("Successfully switched line %d with line %d%n",
                    lineIndexes[0] + 1, lineIndexes[1] + 1);
        } else {
            result =  String.format("Either index %d or index %d is out of bounds.%n",
                    lineIndexes[0] + 1, lineIndexes[1] + 1);
        }
        printToFile(linesInFile, writer);
        return result;
    }

    private static void printToFile(List<String> txt, PrintStream writer) {
        for (int i = 0; i < txt.size(); i++) {
            writer.println(txt.get(i));
        }
        writer.close();
    }

    private static void printFromFileToTextArea(File file) throws IOException {
        StringBuilder lines = new StringBuilder();
        BufferedReader buffy = new BufferedReader(new FileReader(file));
        String l = buffy.readLine();
        while (l != null) {
            lines.append(l).append("\n");
            l = buffy.readLine();
        }

        attempt.FileToTextArea out = new attempt.FileToTextArea(lines.toString());
    }

    private static void printFromFileToConsole(File file) throws IOException {
        System.out.println("\nUpdated text file:");
        BufferedReader buffy = new BufferedReader(new FileReader(file));
        String line = buffy.readLine();
        while (line != null) {
            System.out.println(line);
            line = buffy.readLine();
        }
    }

    private static void swapAB(int indexA, int lineA, int indexB, int lineB, List<String> txtLines) {
        String[] wordsLineA = txtLines.get(lineA).split("\\s+");
        String[] wordsLineB = txtLines.get(lineB).split("\\s+");
        StringBuilder builderA = new StringBuilder();
        StringBuilder builderB = new StringBuilder();
        String wordA = "";
        String wordB = "";
        findFirstWord:
        for (int i = 0; i < wordsLineA.length; i++) {
            if (i != indexA) {
                continue;
            }
            wordA = wordsLineA[i];
            break;
        }
        findSecondWord:
        for (int i = 0; i < wordsLineB.length; i++) {
            if (i != indexB) {
                continue;
            }
            wordB = wordsLineB[i];
            break;
        }
        buildLineOne:
        for (int i = 0; i < wordsLineA.length; i++) {
            if (i != indexA) {
                builderA.append(wordsLineA[i]);
                builderA.append(" ");
                continue;
            }
            builderA.append(wordB);
            builderA.append(" ");
        }
        BuildLineTwo:
        for (int i = 0; i < wordsLineB.length; i++) {
            if (i != indexB) {
                builderB.append(wordsLineB[i]);
                builderB.append(" ");
                continue;
            }
            builderB.append(wordA);
            builderB.append(" ");
        }
        txtLines.remove(lineA);
        txtLines.add(lineA, builderA.toString());
        txtLines.remove(lineB);
        txtLines.add(lineB, builderB.toString());
    }


    private static boolean indexCheck(int[] indexes, List<String> list) {
        return indexes[0] > 0 || indexes[0] < list.size() ||
                indexes[1] > 0 || indexes[1] < list.size();
    }



    private static File readFile(List<String> txtLines, List<String> path) {
        try {
            File filePath = new File(path.get(0));
            BufferedReader buffy = new BufferedReader(new FileReader(filePath));
            String singleLine = buffy.readLine();
            while (singleLine != null) {
                txtLines.add(singleLine);
                singleLine = buffy.readLine();
            }
            buffy.close();
            return filePath;
        } catch (FileNotFoundException ff) {
            System.out.println("File not found.");
            readFile(txtLines, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            path.clear();
        }
        return null;
    }
}
