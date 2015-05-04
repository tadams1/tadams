//
//  OptionInst.h
//  SimpleOptionPricer
//
//  Created by d2d on 18/01/2015.
//  Copyright (c) 2015 d2d. All rights reserved.
//

#ifndef SimpleOptionPricer_OptionInst_h
#define SimpleOptionPricer_OptionInst_h

@interface OptionInst : NSObject

@property double strike;
@property NSDate* maturity;
@property bool isCall;
@property double interestRate;
@property double estVolatility;
@property double undSpot;

@end

#endif
