package com.tadams.chess.piece;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

	public Knight() {
		pieceType = PieceType.KNIGHT;
	}
	
	public int[] getPossibleMoves(int[] board) {
		int[] moveVert = {2,2,-2,-2,1,-1,1,-1};
		int[] moveHorz = {1,-1,1,1,2,2,-2,-2};
		
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = pieceLocation.getLocation();
		for(int i = 0; i< moveVert.length; i++ ) {
			if(MoveGenerator.checkValidLocation(l, moveHorz[i],moveVert[i])) {			
				MoveType mt = MoveGenerator.checkBlockCapture(board,l,moveHorz[i],moveVert[i], colour);
				if(mt != MoveType.BLOCKED){
					legalMoves.add(l+moveHorz[i]+moveVert[i]*8);
				}
			}
		}
		
		int[] moves = new int[legalMoves.size()];
		for(int i = 0; i < legalMoves.size(); i++)
			moves[i]=legalMoves.get(i);
		
		return moves;
	}
	
	
}
