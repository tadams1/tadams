//
//  Game.h
//  pongDemo
//
//  Created by d2d on 28/01/2015.
//  Copyright (c) 2015 d2d. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

int y;
int x;

@interface Game : UIViewController

{
    IBOutlet UIImageView *ball;
    IBOutlet UIButton *startButton;
    
    NSTimer *timer;
    
}

- (IBAction)startButton:(id)sender;
- (void)ballMovement;

@end
