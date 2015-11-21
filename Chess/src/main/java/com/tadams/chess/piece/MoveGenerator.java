package com.tadams.chess.piece;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {

	public static int[] getMovesForPiece(PieceType pt,int location, int[] board, Boolean colour) {
		switch(pt) {
			case BISHOP:
				return getBishopMoves(board, location, colour);
			case KING:
				return getKingMoves(board, location, colour);
			case PAWN:
				return getPawnMoves(board, location, colour);
			case KNIGHT:
				return getKnightMoves(board, location, colour);
			case QUEEN:
				return getQueenMoves(board, location, colour);
			case ROOK:
				return getRookMoves(board, location, colour);
			default:
				return new int[0];
		}
	}
	
	public static int[] getKingMoves(int[] board, int location, Boolean colour) {
		int[] moveVert = {0,0,1,1,1,-1,-1,-1};
		int[] moveHorz = {1,-1,0,1,-1,0,-1,1};
		
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = location;
		for(int i = 0; i< moveVert.length; i++ ) {
			if(checkValidLocation(l, moveHorz[i],moveVert[i])) {
				if(i != 0 && checkValidLocation(l, i,0)) {
					MoveType mt = checkBlockCapture(board,l,i,0, colour);
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
	
	
	public static int[] getBishopMoves(int[] board, int location, Boolean colour) {
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = location;
		int x = l % 8;
		int y = l-(x*8);
		int lo = x < y ? y : x;
		int hi = x < y ? x : y;
		
		
		for(int i = -lo; i< 8-hi; i++ ) {
			if(i != 0 && checkValidLocation(l, i,i)) {
				MoveType mt = checkBlockCapture(board,l,i,i, colour);
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
	
	public static int[] getPawnMoves(int[] board, int location, Boolean colour) {
		int[] moveVert = {1,1,1};
		int[] moveHorz = {-1,0,1};
		
		int dir = colour ? 1 : -1;
		
		
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = location;
		for(int i = 0; i< moveVert.length; i++ ) {
			if(checkValidLocation(l, moveHorz[i],dir * moveVert[i])) {
				legalMoves.add(l+moveHorz[i]+dir * moveVert[i]*8);
			}
		}

		if(!moved) {
			if(checkValidLocation(l, 0, dir * 2)) {
				legalMoves.add(l+2*8*dir);
			}
		}

		int[] moves = new int[legalMoves.size()];
		for(int i = 0; i < legalMoves.size(); i++)
			moves[i]=legalMoves.get(i);
		
		return moves;
	}
	
	public static int[] getKnightMoves(int[] board, int location, Boolean colour) {
		int[] moveVert = {2,2,-2,-2,1,-1,1,-1};
		int[] moveHorz = {1,-1,1,1,2,2,-2,-2};
		
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = location;
		for(int i = 0; i< moveVert.length; i++ ) {
			if(checkValidLocation(l, moveHorz[i],moveVert[i])) {			
				MoveType mt = checkBlockCapture(board,l,moveHorz[i],moveVert[i], colour);
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
	
	
	public static int[] getRookMoves(int[] board, int location, Boolean colour) {
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = location;
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
	
	public static int[] getQueenMoves(int[] board, int location, Boolean colour) {
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = location;
		int x = l % 8;
		int y = ((l-x)/8);
			
		for(int i = 1; i<= 7-x; i++ ) {
			if(i != 0 && checkValidLocation(l, i,0)) {
				MoveType mt = checkBlockCapture(board,l,i,0, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i);
			
				if(mt != MoveType.VALID)
					break;
			}
		}

		for(int i = -1; i<=x; i-- ) {
			if(i != 0 && checkValidLocation(l, i,0)) {
				MoveType mt = checkBlockCapture(board,l,i,0, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i);
			
				if(mt != MoveType.VALID)
					break;
			}
		}

		for(int i = 1; i<= 7-y; i++ ) {
			if(i != 0 && checkValidLocation(l, 0,i)) {
				MoveType mt = checkBlockCapture(board,l,0,i, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i*8);
			
				if(mt != MoveType.VALID)
					break;
				
			}
		}
		
		for(int i = -1; i<=y; i-- ) {
			if(i != 0 && checkValidLocation(l, 0,i)) {
				MoveType mt = checkBlockCapture(board,l,0,i, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i*8);
			
				
				if(mt != MoveType.VALID)
					break;
				
			}
		}
		int lo = x < y ? y : x;
		int hi = x < y ? x : y;
		
		
		for(int i = 1; i<= 7-hi; i++ ) {
			if(i != 0 && checkValidLocation(l, i,i)) {
				MoveType mt = checkBlockCapture(board,l,i,i, colour);
				if(mt != MoveType.BLOCKED)
					legalMoves.add(l+i+i*8);
			
				if(mt != MoveType.VALID)
					break;
			}
		}
		
		for(int i =1; i>= lo; i-- ) {
			if(i != 0 && checkValidLocation(l, i,i)) {
				MoveType mt = checkBlockCapture(board,l,i,i, colour);
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
	

	public static Boolean checkValidLocation(int l, int x, int y) {
		
		if(l+x+y*8<0)
			return false;

		if(l+x+y*8>63)
			return false;
		
		if(l%8+x > 7)
			return false;
		
		if(l%8+x < 0)
			return false;
		
		return true;		
	}
	
	
	public static MoveType checkBlockCapture(int[] board,int c, int x, int y, Boolean colour) {
		
		int l = c+ x + y*8;
		return checkBlockCapture(board,l, colour);

	}

	public static MoveType checkBlockCapture(int[] board,  int l2, Boolean colour) {
		if(board[l2]==-1)
			return MoveType.VALID;
		
		if((board[l2] & 0x00010000) == 0 ^ (colour)) {
			return MoveType.BLOCKED;
		} else {
			return MoveType.CAPTURE;
		}
	}
}
