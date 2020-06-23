package ppc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Orakela1 {
	private final Socket socket;
	private final BufferedReader in;
	private final OutputStream out;

	private int attempts = 0;

	private static final char[] CANDIDATES = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z'};

	public Orakela1() throws IOException {
		socket = new Socket("challs.xmas.htsp.ro", 13000);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = socket.getOutputStream();
	}

	public void solve() throws IOException {
		int prevScore = Integer.MAX_VALUE;
		String guess = "";
		while (true) {
			guess += "A";
			int score = tryGuess(guess);
			if (score < prevScore) {
				prevScore = score;
			} else {
				guess = guess.substring(0, guess.length()-1);
				break;
			}
		}

		System.out.println("Guess: " + guess + " - score: " + prevScore);

		System.out.println("attempts: " + attempts);
		for (int i = 0; i < guess.length(); i++) {
			guess = solveLetter(guess, i);
			System.out.println("Guess: " + guess);
			System.out.println("attempts: " + attempts);
		}
	}

	private String solveLetter(String guess, int idx) throws IOException {
		int l = 0;
		int r = CANDIDATES.length-1;
		int m = (l+r)/2;

		int sl = tryGuess(replace(guess, idx, CANDIDATES[l]));
		int sm = tryGuess(replace(guess, idx, CANDIDATES[m]));
		int sr = tryGuess(replace(guess, idx, CANDIDATES[r]));

		while (true) {
			if (sl <= sm) {
				r = m;
				sr = sm;
				m = (l+r)/2;
				if (m == l) {
					break;
				}
				sm = tryGuess(replace(guess, idx, CANDIDATES[m]));
			} else if (sr <= sm) {
				l = m;
				sl = sm;
				m = (l+r)/2;
				sm = tryGuess(replace(guess, idx, CANDIDATES[m]));
				if (m == r - 1) {
					if (sr < sm) {
						m = r;
					}
					break;
				}
			} else {
				int c1 = (l + m) / 2;
				int sc1 = tryGuess(replace(guess, idx, CANDIDATES[c1]));
				int c2 = (r + m) / 2;
				int sc2 = tryGuess(replace(guess, idx, CANDIDATES[c2]));
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

		return replace(guess, idx, CANDIDATES[m]);
	}

	private String replace(String source, int idx, char c) {
		return source.substring(0, idx) + c + source.substring(idx+1);
	}

	private int tryGuess(String guess) throws IOException {
		attempts++;
		System.out.print("Guessing: " + guess);
		out.write((guess + "\n").getBytes());
		out.flush();
		while (true) {
			String line = in.readLine();
			if (line.contains("Words are made of letters")) {
				System.out.println(" - score: NA");
				return -1;
			}
			if (line.contains("Tell me your guess: ")) {
				int score = Integer.parseInt(line.substring(line.lastIndexOf(' ') + 1));
				System.out.println(" - score: " + score);
				return score;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new Orakela1().solve();
	}
}
