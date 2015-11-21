package com.tadams.chess.piece;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

	public Bishop() {
		pieceType = PieceType.BISHOP;
	}
	
	public int[] getPossibleMoves(int[] board) {
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = pieceLocation.getLocation();
		int x = l % 8;
		int y = l-(x*8);
		int lo = x < y ? y : x;
		int hi = x < y ? x : y;
		
		
		for(int i = -lo; i< 8-hi; i++ ) {
			if(i != 0 && MoveGenerator.checkValidLocation(l, i,i)) {
				MoveType mt = MoveGenerator.checkBlockCapture(board,l,i,i, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i+i*8);
			
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
