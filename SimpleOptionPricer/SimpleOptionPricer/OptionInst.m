//
//  Option.m
//  SimpleOptionPricer
//
//  Created by d2d on 18/01/2015.
//  Copyright (c) 2015 d2d. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "OptionInst.h"

@interface OptionInst()

@end

@implementation OptionInst


-(double)priceOption:(double)test1  {
    
    double d1;
    double d2;
    
    NSDate* now = [NSDate date];
    
    NSTimeInterval secondsBetween = [now timeIntervalSinceDate:_maturity];
    int numberOfDays = secondsBetween / 86400;
    
    d1 = log(_undSpot/_strike)+(_interestRate + _estVolatility+ _estVolatility/2)* numberOfDays/(_estVolatility*sqrt(numberOfDays));
    d2 = d1- _estVolatility*sqrt(numberOfDays);
    
    if(_isCall)
        return _undSpot * [self calcCND:d1]-_strike * exp(-_interestRate*numberOfDays) * [self calcCND:d2];
    else
        return _strike * exp(-_interestRate * numberOfDays) * [self calcCND:-d2] - _strike * [self calcCND:-d1];
        
}

-(double)calcCND:(double)val {
    
    // constants
    double a1 =  0.254829592;
    double a2 = -0.284496736;
    double a3 =  1.421413741;
    double a4 = -1.453152027;
    double a5 =  1.061405429;
    double p  =  0.3275911;
    
    int sign = 1;
    if (val < 0)
        sign = -1;
    
    val = fabs(val)/sqrt(2.0);
    
    // A&S formula 7.1.26
    double t = 1.0/(1.0 + p*val);
    double y = 1.0 - (((((a5*t + a4)*t) + a3)*t + a2)*t + a1)*t*exp(-val*val);
    
    return 0.5*(1.0 + sign*y);
}

@end