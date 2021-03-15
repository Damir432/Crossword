import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
public class CrosswordJR {
        public static void main(String[] args) {
            int[][] crossword = new int[][]{
                    {'f', 'd', 'e', 'r', 'l', 'k'},
                    {'u', 's', 'a', 'm', 'e', 'o'},
                    {'l', 'n', 'g', 'r', 'o', 'v'},
                    {'m', 'l', 'p', 'r', 'r', 'h'},
                    {'p', 'o', 'e', 'e', 'j', 'j'}
            };
            detectAllWords(crossword, "home","same");
            List<Word> wordsList =detectAllWords(crossword, "home","same");
            for(Word w : wordsList)
                System.out.println(w.toString());
        /*
Ожидаемый результат, "same","orgn", "gsf", "meo", "eo"
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
        }

        public static List<Word> detectAllWords(int[][] crossword, String... words) {
            List<Word> wordList = new ArrayList<>();
            int[][] direction = new int[][]{
                    {1, 1, 0,-1,-1,-1,0,1},
                    {0,-1,-1,-1, 0, 1,1,1}
            };
            for (int i = 0; i < words.length; i++) {
                int wordL = words[i].length();
                char[] wordChar = words[i].toCharArray();
                for (int j = 0; j < crossword.length; j++) {
                    for (int k = 0; k < crossword[j].length; k++) {
                        if (crossword[j][k] == (int) wordChar[0]) {
                            if (wordL == 1) {
                                Word newWord = new Word(words[i]);
                                newWord.setStartPoint(k,j);
                                newWord.setEndPoint(k,j);
                                wordList.add(newWord);
                            }
                            //System.out.println((char)crossword[j][k] + " " +j+" " +k);
                            int dirN = 0;
                            while (dirN<8) {
                                int x = direction[0][dirN];
                                int y = direction[1][dirN];
                                if (k+x*(wordL)<= crossword[j].length && k+x*(wordL-1) >=0 && j+y*(wordL)<= crossword.length && j+y*(wordL-1) >=0)
                                    for (int l = 1; l < wordL; l++) {
                                        if (crossword[j + y*l][k + x*l] == (int)wordChar[l]) {
                                            //System.out.println((char)crossword[j + y*l][k + x*l] + " " +(j + y*l)+" " +(k + x*l));
                                            if (l == wordL - 1) {
                                                Word newWord = new Word(words[i]);
                                                newWord.setStartPoint(k,j);
                                                newWord.setEndPoint(k+x*(wordL-1),j+y*(wordL-1));
                                                wordList.add(newWord);
                                            }
                                        }
                                        else break;
                                    }
                                dirN++;
                            }
                        }
                    }
                }
            }
            return wordList;
        }

        public static class Word {
            private String text;
            private int startX;
            private int startY;
            private int endX;
            private int endY;

            public Word(String text) {
                this.text = text;
            }

            public void setStartPoint(int i, int j) {
                startX = i;
                startY = j;
            }

            public void setEndPoint(int i, int j) {
                endX = i;
                endY = j;
            }

            @Override
            public String toString() {
                return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
            }
        }
}
