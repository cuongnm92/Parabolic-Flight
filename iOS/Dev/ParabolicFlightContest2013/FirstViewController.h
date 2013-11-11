//
//  FirstViewController.h
//  ParabolicFlightContest2013
//
//  Created by cuongnm92 on 10/24/13.
//  Copyright (c) 2013 vietnam. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "MeasureViewController.h"

@class MeasureViewController;

@interface FirstViewController : UIViewController

@property (strong, nonatomic) MeasureViewController *measureViewController;

- (IBAction)StartButton:(id)sender;

@end
