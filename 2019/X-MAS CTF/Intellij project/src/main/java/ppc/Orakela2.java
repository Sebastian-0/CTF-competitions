package ppc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Orakela2 {
	private final Socket socket;
	private final BufferedReader in;
	private final OutputStream out;

	private Map<String, Integer> cachedGuesses = new HashMap<>();
	private int attempts = 0;
	private int bestScore = Integer.MAX_VALUE;
	private String guess = "";
	private String flag;

	private static final char[] CANDIDATES = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z'};

	public Orakela2() throws IOException {
		socket = new Socket("challs.xmas.htsp.ro", 13000);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = socket.getOutputStream();
	}

	public void solve() throws IOException {
		System.out.println("attempts: " + attempts);
		while (true) {
			boolean cont = solveLetter();
			if (!cont) {
				break;
			}
			System.out.println("Guess: " + guess + ": " + bestScore);
			System.out.println("attempts: " + attempts);
		}
		System.out.println();
		System.out.println("Correct word: " + guess + " after " + attempts + " attempts");
		System.out.println("The flag was: " + flag);
	}

	private boolean solveLetter() throws IOException {
		int l = 0;
		int r = CANDIDATES.length-1;
		int m = (l+r)/2;

		int sl = tryGuess(append(CANDIDATES[l]));
		int sm = tryGuess(append(CANDIDATES[m]));
		int sr = tryGuess(append(CANDIDATES[r]));

		while (true) {
			if (sl <= sm) {
				r = m;
				sr = sm;
				m = (l+r)/2;
				if (m == l) {
					break;
				}
				sm = tryGuess(append(CANDIDATES[m]));
			} else if (sr <= sm) {
				l = m;
				sl = sm;
				m = (l+r)/2;
				sm = tryGuess(append(CANDIDATES[m]));
				if (m == r - 1) {
					if (sr < sm) {
						m = r;
						sm = sr;
					}
					break;
				}
			} else {
				int c1 = (l + m) / 2;
				int sc1 = tryGuess(append(CANDIDATES[c1]));
				int c2 = (r + m) / 2;
				int sc2 = tryGuess(append(CANDIDATES[c2]));
				if (sc1 < sc2) {
					r = m;
					sr = sm;
					m = c1;
					sm = sc1;
				} else {
					l = m;
					sl = sm;
					m = c2;
					sm = sc2;
				}
			}
		}

		if (sm < bestScore) {
			bestScore = sm;
			guess = append(CANDIDATES[m]);
			return true;
		} else {
			return false;
		}
	}

	private String append(char c) {
		return guess + c;
	}

	private int tryGuess(String guess) throws IOException {
		if (cachedGuesses.containsKey(guess)) {
			return cachedGuesses.get(guess);
		}

		attempts++;
		System.out.print("Guessing: " + guess);
		out.write((guess + "\n").getBytes());
		out.flush();
		while (true) {
			String line = in.readLine();
			if (line == null) {
				System.out.println();
				return Integer.MAX_VALUE;
			}
//			System.out.println(line);
			if (line.contains("Words are made of letters")) {
				System.out.println(" - score: NA");
				return Integer.MAX_VALUE;
			}
			if (line.contains("Here is the True Flag")) {
				flag = line.substring(line.indexOf("X-MAS"));
			}
			if (line.contains("Tell me your guess: ")) {
				int score = Integer.parseInt(line.substring(line.lastIndexOf(' ') + 1));
				System.out.println(" - score: " + score);
				cachedGuesses.put(guess, score);
				return score;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new Orakela2().solve();
	}
}
