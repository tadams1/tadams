package com.tadams.chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.tadams.chess.piece.Bishop;
import com.tadams.chess.piece.King;
import com.tadams.chess.piece.Knight;
import com.tadams.chess.piece.Pawn;
import com.tadams.chess.piece.Piece;
import com.tadams.chess.piece.Queen;
import com.tadams.chess.piece.Rook;

public class ChessEngine {

	private List<Piece> pieces;
	private int iTurn;
	private Boolean gameFinished;
	private int iMaxDepth = 1;
	
	public void InitGameState() {
		iTurn = 0;
		gameFinished = false;
		pieces = new ArrayList<Piece>();
		
		for(int i = 0; i < 8; i++) {
			Pawn p = new Pawn();
			p.initPiece(8+i, true);
			pieces.add(p);
		}
		
		for(int i = 0; i < 8; i++) {
			Pawn p = new Pawn();
			p.initPiece(48+i, false);
			pieces.add(p);
		}
		
		
		Rook r = new Rook();
		r.initPiece(0, true);
		pieces.add(r);

		r = new Rook();
		r.initPiece(7, true);
		pieces.add(r);

		r = new Rook();
		r.initPiece(63, false);
		pieces.add(r);
		
		r = new Rook();
		r.initPiece(56, false);
		pieces.add(r);


		
		Knight k = new Knight();
		k.initPiece(1, true);
		pieces.add(k);

		k = new Knight();
		k.initPiece(6, true);
		pieces.add(k);

		k = new Knight();
		k.initPiece(57, false);
		pieces.add(k);

		k = new Knight();
		k.initPiece(62, false);
		pieces.add(k);

		Bishop b = new Bishop();
		b.initPiece(2, true);
		pieces.add(b);

		b = new Bishop();
		b.initPiece(5, true);
		pieces.add(b);

		b = new Bishop();
		b.initPiece(58, false);
		pieces.add(b);

		b = new Bishop();
		b.initPiece(61, false);
		pieces.add(b);
		
		Queen q = new Queen();
		q.initPiece(3, true);
		pieces.add(q);
		
		q = new Queen();
		q.initPiece(59, false);
		pieces.add(q);
		
		King kg = new King();
		kg.initPiece(4, true);
		pieces.add(kg);
		
		kg = new King();
		kg.initPiece(60, false);
		pieces.add(kg);	
		
	
	}
	
	public int[] buildBoard() {
		int[] board = new int[64];
		for(int i=0; i<board.length;i++) {
			board[i] = -1;
		}
		for(int i=0; i<pieces.size();i++) {
			int boardcode = i;
			if(pieces.get(i).getColour())
				boardcode = boardcode + 0x00010000;
			
			boardcode = boardcode + pieces.get(i).getType().getValue();
			board[pieces.get(i).getLocation()] = boardcode;
				
		}
		
		return board;
	}
	
	@Override
	public String toString() {
		int[] board = buildBoard();
		StringBuilder sb = new StringBuilder();
		for(int i=7;i>=0;i--) {
			for(int j=0;j<8;j++) {
				
				if(board[i*8+j]==-1) {
					sb.append("X");
				} else {
					
					int idx = board[i*8+j] & 0x0000FFFF;
					Piece p = pieces.get(idx);
		
						
					sb.append(p.toString());
				}
				
				sb.append("|");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public void runGame() {
		while(!gameFinished) 	{
			runTurn();
		}
	}
	
	private void runTurn() {
		int[] board = buildBoard();
		Boolean bTurn = (iTurn%2==0);
		System.out.println("Turn started for " + (bTurn ? "White" : "Black"));
		
		
		if(bTurn) {
			Boolean bMoved = false;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			while(!bMoved) {
				System.out.println("Enter piece location to move:");
				int s=0;
				try {
					s = Integer.parseInt(br.readLine());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(board[s] != -1) {
					int idx = board[s] & 0x0000FFFF;
					Piece p = pieces.get(idx);
					if(p.getColour() == bTurn) {
						int[] moves =p.getPossibleMoves(board);
						if(moves.length > 0) {
							StringBuffer sb = new StringBuffer();
							sb.append("Enter piece location to move to valid moves are (");
							for(int j=0;j<moves.length;j++) {
								sb.append(moves[j]);	
								sb.append(",");
							}
							sb.append("):");
							System.out.println(sb.toString());
							int x=0;
							try {
								x = Integer.parseInt(br.readLine());
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							for(int i = 0;i< moves.length;i++) {
								bMoved = bMoved || moves[i] == x;
							}
							if(!bMoved) {				
								System.out.println("Not a valid move");
							} else {
								if(board[x] != -1) {
									//Capture
									int idx1 = board[x] & 0x0000FFFF;
									pieces.remove(idx1);
								}
								board[x] = board[s];
								board[s] = -1; 
								p.setLocation(x);
								
							}
						} else {
							System.out.println("No valid moves for this piece!");
						}
					} else {
						System.out.println("Not your piece!");
					}
						
				} else {
					System.out.println("Piece not found at location");
				}		
			}
		} else {
			generateMove(board, 0, false);
		}
		
		iTurn++;
		System.out.println("Moved completed");
		System.out.println(this.toString());
	}
	
	public void generateMove(int[] board, int iDepth, Boolean clr) {
		
		List<Thread> threads = new ArrayList<Thread>();
		if(iDepth>iMaxDepth)
			return;
		
		int moveVal=-1;
		int idx = 0;
		for(int i=0;i < pieces.size();i++) {
			Piece p = pieces.get(i);
			if(p.getColour() == clr) {
				int[] b = board.clone();
				int v = p.evaluateMoves(b);
				if(v > moveVal) {
					moveVal = v;
					idx = i;
				}
			}
		}
		
		Piece p = pieces.get(idx);
		System.out.println("Making a move on piece: " + p.toString());
		int x = p.getBestMoveLoc();
		int s = p.getLocation();
		if(board[x] != -1) {
			//Capture
			int idx1 = board[x] & 0x0000FFFF;
			pieces.remove(idx1);
		}
		board[x] = board[s];
		board[s] = -1; 
		p.setLocation(x);
	}
}
