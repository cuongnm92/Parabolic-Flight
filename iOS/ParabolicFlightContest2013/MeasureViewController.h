//
//  MeasureViewController.h
//  ParabolicFlightContest2013
//
//  Created by cuongnm92 on 10/22/13.
//  Copyright (c) 2013 vietnam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreMotion/CoreMotion.h>
#import <QuartzCore/QuartzCore.h>


@interface MeasureViewController : UIViewController {
    int currentState;
    
    NSOperationQueue *_deviceMotionQueue;
    NSOperationQueue *_accelQueue;
    NSOperationQueue *_gyroQueue;
    
    NSString *_gravityString;
    NSString *_rawGyroscopeString;
    NSString *_rawAccelerometerString;
    
    
    bool _logGravityData;
    bool _logRawGyroscopeData;
    bool _logRawAccelerometerData;
    long long timeBegin;
}

- (IBAction)ReturnButtonAction:(id)sender;

- (IBAction)StartButtonAction:(id)sender;

@property (strong, nonatomic) IBOutlet UIButton *ReturnButton;

@property (strong, nonatomic) IBOutlet UIButton *StartButton;

@property (strong, nonatomic) IBOutlet UILabel *gravityZ;

@property (strong, nonatomic) IBOutlet UILabel *gravityY;

@property (strong, nonatomic) IBOutlet UILabel *gravityX;

@property (strong, nonatomic) IBOutlet UILabel *accelerometerZ;

@property (strong, nonatomic) IBOutlet UILabel *accelerometerY;

@property (strong, nonatomic) IBOutlet UILabel *accelerometerX;

@property (strong, nonatomic) IBOutlet UILabel *GyroscopeZ;

@property (strong, nonatomic) IBOutlet UILabel *GyroscopeY;

@property (strong, nonatomic) IBOutlet UILabel *GyroscopeX;

@property (strong, nonatomic) CMMotionManager *motionManager;


- (void) startLoggingMotionData;
- (void) stopLoggingMotionDataAndSave;
- (void) setLogGravityData:(bool)newValue;
- (void) setLogRawGyroscopeData:(bool)newValue;
- (void) setLogRawAccelerometerData:(bool)newValue;

@end
