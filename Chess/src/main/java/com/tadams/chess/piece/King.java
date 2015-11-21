package com.tadams.chess.piece;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

	public King() {
		pieceType = PieceType.KING;
	}
	
	public int[] getPossibleMoves(int[] board) {
		int[] moveVert = {0,0,1,1,1,-1,-1,-1};
		int[] moveHorz = {1,-1,0,1,-1,0,-1,1};
		
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = pieceLocation.getLocation();
		for(int i = 0; i< moveVert.length; i++ ) {
			if(MoveGenerator.checkValidLocation(l, moveHorz[i],moveVert[i])) {
				if(i != 0 && MoveGenerator.checkValidLocation(l, i,0)) {
					MoveType mt = MoveGenerator.checkBlockCapture(board,l,i,0, colour);
					if(mt != MoveType.BLOCKED)
						legalMoves.add(l+i);
				}
			}
		}
		
		int[] moves = new int[legalMoves.size()];
		for(int i = 0; i < legalMoves.size(); i++)
			moves[i]=legalMoves.get(i);
		
		return moves;
	}
	

}
