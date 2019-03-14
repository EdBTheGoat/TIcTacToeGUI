package ttt_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TTT_GUI extends JFrame{
	private JPanel jpMain, scoreBoard;
	TTTBoard jpBoard;
	private JLabel top, bottom;
	
	private Player currPlayer;
	private Player player1;
	private Player player2;
	
	private int numWins1;
	private int numWins2;
	private int totalGames;
	
	public TTT_GUI(){
		player1 = new Player("Wonderwoman", "W", Color.YELLOW); 
		player2 = new Player("Catwoman", "C", Color.BLUE);
		currPlayer = player1;
		
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());
		jpMain.setBackground(Color.CYAN);
		
		scoreBoard = new JPanel();
		scoreBoard.setLayout(new GridLayout(3,3));
		scoreBoard.setBackground(Color.CYAN);
		
		top = new JLabel("HEY");
		top.setFont(top.getFont().deriveFont(25f));
		
		bottom = new JLabel(player1.getName() + ": " + numWins1 + " " + player2.getName() + ": " + numWins2 + " Total games played: " + totalGames);
		bottom.setFont(bottom.getFont().deriveFont(25f));
		
		jpBoard = new TTTBoard();
		scoreBoard.add(BorderLayout.NORTH, top);
		scoreBoard.add(BorderLayout.CENTER, bottom);
		jpMain.add(BorderLayout.CENTER, jpBoard);
		
		add(scoreBoard, BorderLayout.NORTH);
		add(jpMain);
		setSize(700,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class ScoreBoard extends JPanel{
		
	}
	
	private class TTTBoard extends JPanel implements GameBoardInterface, GamePlayerInterface, ActionListener{
		private JButton [][] board;
		private final int ROWS = 3;
		private final int COLS = 3;
		
		public TTTBoard(){
			setLayout(new GridLayout(ROWS,COLS));
			board = new JButton[ROWS][COLS];
			displayBoard();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();
			btnClicked.setText(currPlayer.getSymbol());
			btnClicked.setEnabled(false);
			if(isWinner() == true){
				if(currPlayer == player1) {
					numWins1++;
					totalGames++;
				}else {
					numWins2++;
					totalGames++;
				}
				bottom.setText(player1.getName() + ": " + numWins1 + " " + player2.getName() + ": " + numWins2 + " Total games played: " + totalGames); 
				JOptionPane.showMessageDialog(null, "WINNER= "+currPlayer.getName());
				int reply = JOptionPane.showConfirmDialog(null, "Want to play again?", "Hm?", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_OPTION) {
					clearBoard();
				}else {
					System.exit(0);
				}
			}
			else if(isFull()){
				totalGames++;
				bottom.setText(player1.getName() + ": " + numWins1 + " " + player2.getName() + ": " + numWins2 + " Total games played: " + totalGames); 
				JOptionPane.showMessageDialog(null,"IS FULL... DRAW");
				int reply = JOptionPane.showConfirmDialog(null, "Want to play again?", "Hm?", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_OPTION) {
					clearBoard();
				}else {
					System.exit(0);
				}
			}
			takeTurn();
		}


		@Override
		public void displayBoard() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col] = new JButton();
					Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 40);
					board[row][col].setFont(bigFont);
					board[row][col].addActionListener(this);
					board[row][col].setEnabled(true);
					add(board[row][col]);	
				}
			}
		}

		@Override
		public void clearBoard() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col].setText("");
					board[row][col].setEnabled(true);
				}
			}
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public boolean isFull() {
			for(int row=0; row < board.length; row++){
				for(int col=0; col< board[row].length; col++){
					String cellContent = board[row][col].getText().trim();
					if(cellContent.isEmpty()){
						return false;
					}
				}
			}
			
			return true;
		}

		@Override
		public boolean isWinner() {
			if(isWinnerInRow() || isWinnerInCol() || isWinnerInMainDiag() || isWinnerInSecDiag()){
				return true;
			}
			return false;
		}

		public boolean isWinnerInRow(){
			String symbol = currPlayer.getSymbol();
			for(int row=0; row < board.length; row++){
				int numMatchesInRow = 0; 
				for(int col=0; col< board[row].length; col++){
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInRow++;
					}
				}
				if(numMatchesInRow == 3){
					return true;
				}
			}
			return false;
		}
		
		public boolean isWinnerInCol(){
			String symbol = currPlayer.getSymbol();
			for(int col=0; col<board.length; col++){
				int matches = 0;
				for(int row=0; row<3; row++){
					if(board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						matches++;
					}
				}
				if(matches == 3){
					return true; 
				}
			}
			return false;
		}
		
		public boolean isWinnerInMainDiag(){
			String symbol = currPlayer.getSymbol();
			int matches = 0;
			for(int i=0; i<board.length; i++){
				if(board[i][i].getText().trim().equalsIgnoreCase(symbol)){
					matches++;
				}
			}
			if(matches == 3){
				return true;
			}
			return false;
		}
		
		public boolean isWinnerInSecDiag() {
			String symbol = currPlayer.getSymbol();
			int matches = 0;
			int row = 2;
			int col=0;
			while(row >-1 && col < 3){
				if(board[row][col].getText().trim().equalsIgnoreCase(symbol)){
					matches++;
				}
				row--;
				col++;
			}
			if(matches == 3){
				return true; 
			}
			return false;
		}
		
		@Override
		public void takeTurn() {
			if(currPlayer.equals(player1)){
				currPlayer = player2;
				top.setText("The current player is: " + currPlayer.getName() + "(" + currPlayer.getSymbol() + ")");
			}
			else{
				currPlayer = player1;
				top.setText("The current player is: " + currPlayer.getName() + "(" + currPlayer.getSymbol() + ")");
			}
		}
		
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run() {
				TTT_GUI gui = new TTT_GUI();
			}
		});
	}

}