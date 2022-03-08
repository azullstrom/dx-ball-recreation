package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

import states.GameState;

public class HighScoreWindow implements ActionListener {
	
	private JButton submitButton;
	private MyStack<String> highScoreStack, latestRunStack, nameStack, newHighStack;
	private JList<String> scoreList, runList, nameList, newHighList;
	private JPanel highScorePanel, latestRunPanel, newHighPanel;
	private String[] str, str2, str3, readScore, readNames, readLatest;
	private int tickCount, tickCountUp, tickCountDown, tickCountEnter, letters, score, a, b, c, pressed;
	private char ch;
	private String name, nameFile, scoreFile, latestFile;
	private Border border, borderTwo;
	private Keyboard keyboard;
	private char[] alphabet;
	private Font font;
	private boolean newHighScore;
	private GameBoard board;
	
	public HighScoreWindow(int score, GameBoard board) {
		super();
		board.setSize(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
		this.board = board;
		
		readScore = new String[10];
		readNames = new String[10];
		readLatest = new String[3];
		this.score = score;
		
		ch = 'A';
		alphabet = new char[26];
		str = new String[26];
		str2 = new String[26];
		str3 = new String[26];
		for(int i = 0; i < 26; i++) {
			alphabet[i] = ch++;
			str[i] = String.valueOf(alphabet[i]);
			str2[i] = String.valueOf(alphabet[i]);
			str3[i] = String.valueOf(alphabet[i]);
		}
		
		nameFile = "saveName.txt";
		scoreFile = "saveScore.txt";
		latestFile = "latestScore.txt";
		
		highScoreStack = new MyStack<String>(10);
		
		newHighStack = new MyStack<String>(1);
		
		nameStack = new MyStack<String>(3);
		
		latestRunStack = new MyStack<String>(3);
		
		readFiles();
		
		border = BorderFactory.createLineBorder(Color.orange, 10);
		borderTwo = BorderFactory.createLineBorder(Color.orange, 10);
		
		highScorePanel = new JPanel();
		highScorePanel.setSize(300, 450);
		highScorePanel.setLocation(50, 50);
		highScorePanel.setBorder(border);
		highScorePanel.setBackground(Color.darkGray);
		highScorePanel.setLayout(new GridLayout(1, 2));
		
		newHighPanel = new JPanel();
		newHighPanel.setSize(300, 35);
		newHighPanel.setLocation(50, 550);
		newHighPanel.setBackground(Color.darkGray);
		newHighPanel.setLayout(new GridLayout(1, 2));
		
		latestRunPanel = new JPanel();
		latestRunPanel.setSize(300, 500);
		latestRunPanel.setLocation(450, 50);
		latestRunPanel.setBorder(borderTwo);
		latestRunPanel.setBackground(Color.darkGray);
		
		scoreList = new JList<String>(highScoreStack.getModel());
		scoreList.setFont(new Font("Verdana", Font.BOLD, 30));
		scoreList.setBackground(Color.darkGray);
		scoreList.setForeground(Color.orange);
		
		newHighList = new JList<String>(newHighStack.getModel());
		newHighList.setFont(new Font("Verdana", Font.BOLD, 30));
		newHighList.setBackground(Color.darkGray);
		newHighList.setForeground(Color.orange);
		
		nameList = new JList<String>(nameStack.getModel());
		nameList.setFont(new Font("Verdana", Font.BOLD, 30));
		nameList.setBackground(Color.darkGray);
		nameList.setForeground(Color.orange);
		
		runList = new JList<String>(latestRunStack.getModel());
		runList.setFont(new Font("Verdana", Font.BOLD, 30));
		runList.setBackground(Color.darkGray);
		runList.setForeground(Color.orange);
		
		highScorePanel.add(nameList);
		highScorePanel.add(scoreList);
		newHighPanel.add(newHighList);
		latestRunPanel.add(runList);
		
		submitButton = new JButton("SUBMIT");
		submitButton.setSize(300, 50);
		submitButton.setLocation(50, 500);
		submitButton.setContentAreaFilled(false);
		submitButton.setBorder(border);
		submitButton.setBackground(Color.darkGray);
		submitButton.setForeground(Color.orange);
		submitButton.setFont(new Font("Verdana", Font.BOLD, 20));
		submitButton.setOpaque(true);
		
		board.add(latestRunPanel);
		board.add(highScorePanel);
		board.add(submitButton);
		
		submitButton.addActionListener(this);
	}
	
	private void readFiles() {
		try {
	        File nf = new File(nameFile);
	        Scanner myReader = new Scanner(nf);
	        int x = 0;
	        while (myReader.hasNextLine()) {
		        readNames[x] = myReader.nextLine();
		        x++;
	        }
	        myReader.close();
        } catch (FileNotFoundException e) {
	        System.out.println("Error");
	        e.printStackTrace();
        }
		
		try {
	        File lf = new File(latestFile);
	        Scanner myReader = new Scanner(lf);
	        int x = 0;
	        while (myReader.hasNextLine() && x < 3) {
		        readLatest[x] = myReader.nextLine();
		        x++;
	        }
	        myReader.close();
        } catch (FileNotFoundException e) {
	        System.out.println("Error");
	        e.printStackTrace();
        }
		
		try {
			FileWriter w = new FileWriter(latestFile);
			String temp = readLatest[0];
			String temp2 = readLatest[1];
			
			readLatest[0] = Integer.toString(score);
			readLatest[1] = temp;
			readLatest[2] = temp2;
			
			for(int i = 0; i < 3; i++) {
				w.write(readLatest[i] + System.lineSeparator());
			}
			int i = 2;
			while(i >= 0) {
				latestRunStack.push(readLatest[i]);
				i--;
			}
			w.close();
			
			System.out.println("Skrev latest");
		} catch (IOException e1) {
			System.out.println("Error");
			e1.printStackTrace();
		}
		
		try {
	        File sf = new File(scoreFile);
	        Scanner myReader = new Scanner(sf);
	        int x = 0;
	        while (myReader.hasNextLine() && x < 10) {
		        readScore[x] = myReader.nextLine();
		        x++;
	        }
	        myReader.close();
	        for(int i = 0; i < 10; i++) {
	        	for(int j = 0; j < 10; j++) {
	        		if(readScore[i] != null && readScore[j] != null) {
		        		if(Integer.parseInt(readScore[i]) < Integer.parseInt(readScore[j])) {
		        			String temp = readScore[i];
		        			readScore[i] = readScore[j];
		        			readScore[j] = temp;
		        			
		        			String tempTwo = readNames[i];
		        			readNames[i] = readNames[j];
		        			readNames[j] = tempTwo;
		        		}
	        		}
	        	}
	        }
	        for(int i = 0; i < readScore.length; i++) {
	        	if(readScore[i] != null) {
	        		highScoreStack.push(readScore[i]);
	        		nameStack.push(readNames[i]);
	        	}
	        }
        } catch (FileNotFoundException e) {
	        System.out.println("Error");
	        e.printStackTrace();
        }
	}

	public void update(Keyboard keyboard) {
		this.keyboard = keyboard;
	}
	
	public void draw(Graphics2D graphics, Font font) {
		this.font = font;
		
		graphics.setFont(font);
		graphics.setColor(Color.orange);
		graphics.drawString("HIGHSCORE", 100, 40);
		graphics.drawString("LATEST RUNS", 490, 40);
		for(int i = 0; i < 10; i++) {
			graphics.drawString(Integer.toString(i+1) + ".", 6, 92+(40*i));
		}
		for(int i = 0; i < 3; i++) {
			graphics.drawString(Integer.toString(i+1) + ".", 406, 92+(40*i));
		}
		graphics.drawRect(5, 5, Constant.WINDOW_WIDTH - 10, Constant.WINDOW_HEIGHT - 10);
		graphics.fillRect(400, 5, 1, 590);
		
		namePicker(graphics);
	}
	
	private void namePicker(Graphics2D graphics) {
		graphics.setFont(font);
		graphics.setColor(Color.orange);
		
		// First char
		if(letters == 0) {
			tickCount++;
			if(tickCount == 1) {
				for(int i = 0; i < 10; i++) {
					if(readScore[i] != null) {
						if(score >= Integer.parseInt(readScore[i])) {
							newHighScore = true;
						}
					} else if(!highScoreStack.isFull()) {
						newHighScore = true;
					}
				}
				if(newHighScore) {
					board.add(newHighPanel);
					newHighStack.push(str[a] + "           " + Integer.toString(score));
				} else {
					System.out.println("Not high score");
				}		
			}
			
			// Roll up
			if(keyboard.isKeyDown(Key.Up)) {
				tickCountUp++;
				if(tickCountUp == 1) {
					newHighStack.pop();
					tickCount = 0;
					++a;
					if(a >= str.length) {
						a = 0;
					}
				}
			} else if(keyboard.isKeyUp(Key.Up)) {
				tickCountUp = 0;
			}
			
			// Roll down
			if(keyboard.isKeyDown(Key.Down)) {
				tickCountDown++;
				if(tickCountDown == 1) {
					newHighStack.pop();
					tickCount = 0;
					--a;
					if(a < 0) {
						a = str.length - 1;
					}
				}
			} else if(keyboard.isKeyUp(Key.Down)) {
				tickCountDown = 0;
			}
		}
		
		// Second char
		if(letters == 1) {
			tickCount++;
			if(tickCount == 1) {
				newHighStack.pop();
				newHighStack.push(str[a] + str2[b] + "          " + Integer.toString(score));
			}
			
			// Roll up
			if(keyboard.isKeyDown(Key.Up)) {
				tickCountUp++;
				if(tickCountUp == 1) {
					tickCount = 0;
					++b;
					if(b >= str2.length) {
						b = 0;
					}
				}
			} else if(keyboard.isKeyUp(Key.Up)) {
				tickCountUp = 0;
			}
			
			// Roll down
			if(keyboard.isKeyDown(Key.Down)) {
				tickCountDown++;
				if(tickCountDown == 1) {
					tickCount = 0;
					--b;
					if(b < 0) {
						b = str2.length - 1;
					}
				}
			} else if(keyboard.isKeyUp(Key.Down)) {
				tickCountDown = 0;
			}	
		}
		
		// Third char
		if(letters == 2) {
			tickCount++;
			if(tickCount == 1) {
				newHighStack.pop();
				newHighStack.push(str[a] + str2[b] + str3[c] + "         " + Integer.toString(score));
			}
			
			// Roll up
			if(keyboard.isKeyDown(Key.Up)) {
				tickCountUp++;
				if(tickCountUp == 1) {
					tickCount = 0;
					++c;
					if(c >= str3.length) {
						c = 0;
					}
				}
			} else if(keyboard.isKeyUp(Key.Up)) {
				tickCountUp = 0;
			}
			
			// Roll down
			if(keyboard.isKeyDown(Key.Down)) {
				tickCountDown++;
				if(tickCountDown == 1) {
					tickCount = 0;
					--c;
					if(c < 0) {
						c = str3.length - 1;
					}
				}
			} else if(keyboard.isKeyUp(Key.Down)) {
				tickCountDown = 0;
			}
		}
		
		// Enter
		if(keyboard.isKeyDown(Key.Enter)) {
			tickCountEnter++;
			tickCount = 0;
			if(tickCountEnter == 1) {
				++letters;
			}
		} else if(keyboard.isKeyUp(Key.Enter)) {
			tickCountEnter = 0;
		}
		
		// When entered all chars
		if(letters == 0) {
			name = str[a];
		}
		if(letters == 1) {
			name = str[a] + str2[b];
		}
		if(letters >= 2) {
			name = str[a] + str2[b] + str3[c];
		}
			
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submitButton) {
			pressed++;
			if(pressed == 1)
				submitButton.setText("EXIT");
			if(pressed == 2)
				System.exit(0);
			
			submitButton.setBackground(Color.black);
			if(highScoreStack.isFull()) {
				for(int i = 0; i < 10; i++) {
	        		if(score >= Integer.parseInt(highScoreStack.getPosition(i))) {
	        			highScoreStack.pop(highScoreStack.getModel().getSize() - 1);
	        			nameStack.pop(highScoreStack.getModel().getSize() - 1);
	        			
	        			highScoreStack.push(Integer.toString(score), i);
	        			nameStack.push(name, i);
	        			break;
	        		}
		        }
				for(int i = 0; i < 10; i++) {
	        		if(highScoreStack.getPosition(i) != null) {
		        		readScore[i] = highScoreStack.getPosition(i);
		        		readNames[i] = nameStack.getPosition(i);
		        		System.out.println(readScore[i] + " " + readNames[i]);
	        		}
		        }
				
				try {
					FileWriter w = new FileWriter(nameFile);
					for(int i = 0; i < 10; i++)
						w.write(readNames[i] + System.lineSeparator());
					w.close();
					
					w = new FileWriter(scoreFile);
					for(int i = 0; i < 10; i++)
						w.write(readScore[i] + System.lineSeparator());
					w.close();
				} catch (IOException e1) {
					System.out.println("Error");
					e1.printStackTrace();
				}
			} 
		}
	}
}
