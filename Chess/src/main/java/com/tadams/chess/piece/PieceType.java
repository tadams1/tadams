package com.tadams.chess.piece;

public enum PieceType {

	PAWN(0x01000000),
	ROOK(0x05000000),
	KNIGHT(0x03000000),
	BISHOP(0x03000000),
	QUEEN(0x09000000),
	KING(0x0F000000);
	
	private final int pieceValue;
	private PieceType(int value) {
		pieceValue = value;
	}
	
	public int getValue() {
		return pieceValue;
	}
}
