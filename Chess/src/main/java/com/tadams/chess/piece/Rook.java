package com.tadams.chess.piece;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

	public Rook() {
		pieceType = PieceType.ROOK;
	}
	
	public int[] getPossibleMoves(int[] board) {
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = pieceLocation.getLocation();
		int x = l % 8;
		int y = ((l-x)/8);
		
		for(int i = 1; i<=7-x; i++ ) {
			if(i != 0 && MoveGenerator.checkValidLocation(l, i,0)) {
				MoveType mt = MoveGenerator.checkBlockCapture(board,l,i,0, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i);
			
				if(mt != MoveType.VALID)
					break;
			}
		}

		for(int i = -1; i>=x; i-- ) {
			if(i != 0 && MoveGenerator.checkValidLocation(l, i,0)) {
				MoveType mt = MoveGenerator.checkBlockCapture(board,l,i,0, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i);
			
				if(mt != MoveType.VALID)
					break;
			}
		}
		
		for(int i = 1; i<= 7-y; i++ ) {
			if(i != 0 && MoveGenerator.checkValidLocation(l, 0,i)) {
				MoveType mt = MoveGenerator.checkBlockCapture(board,l,0,i, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i*8);
			
				if(mt != MoveType.VALID)
					break;
				
			}
		}
		
		for(int i = -1; i<=y; i-- ) {
			if(i != 0 && MoveGenerator.checkValidLocation(l, 0,i)) {
				MoveType mt = MoveGenerator.checkBlockCapture(board,l,0,i, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i*8);
			
				if(mt != MoveType.VALID)
					break;
				
			}
		}
		
		int[] moves = new int[legalMoves.size()];
		for(int i = 0; i < legalMoves.size(); i++)
			moves[i]=legalMoves.get(i);
		
		return moves;
	}
	

}
