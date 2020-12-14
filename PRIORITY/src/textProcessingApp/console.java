package textProcessingApp;

import java.io.*;
        import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class console {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        List<String> linesInFile = new ArrayList<>();
        File file = readFile(scan, linesInFile);
        if (file == null) {
            System.out.println("Unsuccessful.");
            return;
        }

        int choice = getDirections(scan);
        while (choice != 3) {
            PrintStream struma = new PrintStream(new FileOutputStream(file));
            switch (choice) {
                case 1:
                    System.out.println(switchLines(scan, linesInFile, struma));
                    break;
                case 2:
                    try {
                        System.out.println((switchTwoWords(scan, linesInFile, struma)));
                    } catch (IndexOutOfBoundsException ff) {
                        System.out.println(ff.getMessage());
                        choice = getDirections(scan);
                        continue;
                    }
                    break;
            }
            printFromFileToConsole(file);
            choice = getDirections(scan);
        }
        linesInFile.clear();
    }

    private static String switchTwoWords(Scanner scan, List<String> txtLines, PrintStream writer) {
        int[] indexes = getWordIndexes(scan);
        if (indexWordCheck(indexes, txtLines)) {
            int firstWordIndex = indexes[1];
            int firstWordLineIndex = indexes[0];
            int secondWordIndex = indexes[3];
            int secondWordLineIndex = indexes[2];
            swapAB(firstWordIndex, firstWordLineIndex, secondWordIndex, secondWordLineIndex, txtLines);
            printToFile(txtLines, writer);
            return "\nSuccessfully switched the words";
        } else {
            throw new IndexOutOfBoundsException(String.format("Index out of bounds: %s",
                    Arrays.stream(indexes).map(e -> e + 1).mapToObj(String::valueOf).collect(Collectors.joining(" or "))));
        }
    }

    private static boolean indexWordCheck(int[] indexes, List<String> txtLines) {

        if (indexes[0] >= 0 && indexes[1] >= 0 && indexes[2] >= 0 && indexes[3] >= 0) {
            if (indexes[0] < txtLines.size()  && indexes[2] < txtLines.size()) {
                return indexes[1] < txtLines.get(indexes[0]).split("\\s+").length &&
                indexes[3] < txtLines.get(indexes[2]).split("\\s+").length;

            }
        }
        return false;
    }


    private static int[] getWordIndexes(Scanner scan) {
        System.out.printf("Enter each line-word index pair, counting from 1\nseparated by a total of 3 spaces\n");
        int[] indexes = Arrays.stream(scan.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .map(e -> e - 1)
                .toArray();
        if (indexes.length != 4) {
            System.out.println("Invalid input");
            return getWordIndexes(scan);
        }
        return indexes;
    }

    private static int getDirections(Scanner scan) {
        System.out.println();
        System.out.println("Enter below the number of chosen option");
        System.out.print("1. Switch two lines from file\n2. Switch two words from file\n3. Exit the program, file remains unchanged\n");
        try {
            return Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException ff) {
            System.out.println("Invalid input, try again.");
            return getDirections(scan);
        }
    }

    private static String switchLines(Scanner scan, List<String> linesInFile, PrintStream writer) {
        String result = "";
        int[] lineIndexes = getLineIndexes(scan);
        if (indexCheck(lineIndexes, linesInFile)) {
            Collections.swap(linesInFile, lineIndexes[0], lineIndexes[1]);
            result =  String.format("Successfully switched line %d with line %d",
                    lineIndexes[0] + 1, lineIndexes[1] + 1);
        } else {
            System.out.println(String.format("Either index %d or index %d is out of bounds.",
                    lineIndexes[0] + 1, lineIndexes[1] + 1));
            return switchLines(scan, linesInFile, writer);
        }

        printToFile(linesInFile, writer);
        return result;
    }

    private static int[] getLineIndexes(Scanner scan) {
        System.out.printf("Enter two line indexes, counting from 1, seperated by space\n");
        int[] lineIndexes = Arrays.stream(scan.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .map(e -> e - 1)
                .toArray();
        if (lineIndexes.length != 2) {
            System.out.println("Invalid input");
            return getLineIndexes(scan);
        }
        return lineIndexes;
    }

    private static void printToFile(List<String> txt, PrintStream writer) {
        for (String s : txt) {
            writer.println(s);
        }
        writer.close();
    }

    private static void printFromFileToConsole(File file) throws IOException {
        System.out.println("\nUpdated text file:");
        System.out.println("==================");
        BufferedReader buffy = new BufferedReader(new FileReader(file));
        String line = buffy.readLine();
        while (line != null) {
            System.out.println(line);
            line = buffy.readLine();
        }
        System.out.println("==================");
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
        return (indexes[0] >= 0 && indexes[0] < list.size()) ||
                (indexes[1] >= 0 && indexes[1] < list.size());
    }

    private static File readFile(Scanner scan, List<String> txtLines) {
        System.out.printf("Enter path to file:");
        try {
            File filePath = new File(scan.nextLine());
            BufferedReader buffy = new BufferedReader(new FileReader(filePath));
            String singleLine = buffy.readLine();
            while (singleLine != null) {
                txtLines.add(singleLine);
                singleLine = buffy.readLine();
            }
            buffy.close();
            return filePath;
        } catch (FileNotFoundException ff) {
            System.out.println("File was not found.");
            return readFile(scan, txtLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

