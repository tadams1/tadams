//
//  ViewController.m
//  pongDemo
//
//  Created by d2d on 28/01/2015.
//  Copyright (c) 2015 d2d. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    AnimatedBackground.animationImages = [NSArray arrayWithObjects:
                                          [UIImage imageNamed:@"Desktop1.png"],
                                          [UIImage imageNamed:@"Desktop2.png"],
                                          [UIImage imageNamed:@"Desktop3.png"],
                                          [UIImage imageNamed:@"Desktop4.png"],
                                          [UIImage imageNamed:@"Desktop5.png"],nil];
    [AnimatedBackground setAnimationRepeatCount:0];
    AnimatedBackground.animationDuration = 5;
    [AnimatedBackground startAnimating];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
