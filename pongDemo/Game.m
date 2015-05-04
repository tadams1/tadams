//
//  Game.m
//  pongDemo
//
//  Created by d2d on 28/01/2015.
//  Copyright (c) 2015 d2d. All rights reserved.
//

#import "Game.h"

@implementation Game

-(IBAction)startButton:(id)sender {
    
    y= arc4random() % 11;
    y = y -5;
    
    x = arc4random() % 11;
    x = x -5;
    
    if(y==0)
    {
        y=1;
    }
    
    if(x==0)
    {
        x=1;
    }
    
    timer = [NSTimer scheduledTimerWithTimeInterval:0.01 target:self selector:@selector(ballMovement) userInfo:nil repeats:YES];
    
    
    
}

-(void)ballMovement
{
    ball.center = CGPointMake(ball.center.x +x, ball.center.y + y);
    
    if(ball.center.x < 15)
    {
        x = 0-x;
    }
    
    if(ball.center.x > 305)
    {
        x = 0-x;
    }
    
    
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
