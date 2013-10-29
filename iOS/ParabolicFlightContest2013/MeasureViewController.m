//
//  MeasureViewController.m
//  ParabolicFlightContest2013
//
//  Created by cuongnm92 on 10/22/13.
//  Copyright (c) 2013 vietnam. All rights reserved.
//

#import "MeasureViewController.h"

@interface MeasureViewController ()

@end

@implementation MeasureViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.motionManager = [[CMMotionManager alloc] init];
    self.motionManager.accelerometerUpdateInterval = 0.1;
    self.motionManager.gyroUpdateInterval = 0.1;
    self.motionManager.deviceMotionUpdateInterval = 0.1;
    currentState = 0;
    // self.motionManager.
    
    // Limiting the concurrent ops to 1 is a cheap way to avoid two handlers editing the same
    // string at the same time.
    _deviceMotionQueue = [[NSOperationQueue alloc] init];
    [_deviceMotionQueue setMaxConcurrentOperationCount:1];
    
    _accelQueue = [[NSOperationQueue alloc] init];
    [_accelQueue setMaxConcurrentOperationCount:1];
    
    _gyroQueue = [[NSOperationQueue alloc] init];
    [_gyroQueue setMaxConcurrentOperationCount:1];
    
    _gravityString = [[NSString alloc] init];
    _rawGyroscopeString = [[NSString alloc] init];
    _rawAccelerometerString = [[NSString alloc] init];
    
    if (currentState == 0) {
           self.accelerometerX.text = [NSString stringWithFormat:@"X : %.3f",0.0];
           self.accelerometerY.text = [NSString stringWithFormat:@"Y : %.3f",0.0];
           self.accelerometerZ.text = [NSString stringWithFormat:@"Z : %.3f",0.0];
        
           self.GyroscopeX.text = [NSString stringWithFormat:@"X : %.3f",0.0];
           self.GyroscopeY.text = [NSString stringWithFormat:@"Y : %.3f",0.0];
           self.GyroscopeZ.text = [NSString stringWithFormat:@"Z : %.3f",0.0];
        
           self.gravityX.text = [NSString stringWithFormat:@"X : %.3f",0.0];
           self.gravityY.text = [NSString stringWithFormat:@"X : %.3f",0.0];
           self.gravityZ.text = [NSString stringWithFormat:@"X : %.3f",0.0];
    }
}

- (void) startLoggingMotionData {
    
    NSLog(@"Starting to log motion data.");
    
    CMDeviceMotionHandler motionHandler = ^(CMDeviceMotion *motion, NSError *error) {
        [self processMotion:motion withError:error];
    };
    
    CMGyroHandler gyroHandler = ^(CMGyroData *gyroData, NSError *error) {
        [self processGyro:gyroData withError:error];
    };
    
    CMAccelerometerHandler accelHandler = ^(CMAccelerometerData *accelerometerData, NSError *error) {
        [self processAccel:accelerometerData withError:error];
    };
    
    
    [_motionManager startDeviceMotionUpdatesToQueue:_deviceMotionQueue withHandler:motionHandler];
    
    [_motionManager startGyroUpdatesToQueue:_gyroQueue withHandler:gyroHandler];
    
    [_motionManager startAccelerometerUpdatesToQueue:_accelQueue withHandler:accelHandler];
}

- (void) stopLoggingMotionDataAndSave {
    
    NSLog(@"Stopping data logging.");
    
    [_motionManager stopDeviceMotionUpdates];
    [_deviceMotionQueue waitUntilAllOperationsAreFinished];
    
    [_motionManager stopAccelerometerUpdates];
    [_accelQueue waitUntilAllOperationsAreFinished];
    
    [_motionManager stopGyroUpdates];
    [_gyroQueue waitUntilAllOperationsAreFinished];
    
    // Save all of the data!
    [self writeDataToDisk];
    
}

- (void) processAccel:(CMAccelerometerData*)accelData withError:(NSError*)error {
    
     _rawAccelerometerString = [_rawAccelerometerString stringByAppendingFormat:@"%f,%f,%f,%f\n", accelData.timestamp,
                                   accelData.acceleration.x,
                                   accelData.acceleration.y,
                                   accelData.acceleration.z,
                                   nil];
    
    [[NSOperationQueue mainQueue] addOperationWithBlock:^{
        
        //Do any updates to your label here
        self.accelerometerX.text = [NSString stringWithFormat:@"X : %.3f",accelData.acceleration.x];
        self.accelerometerY.text = [NSString stringWithFormat:@"Y : %.3f",accelData.acceleration.y];
        self.accelerometerZ.text = [NSString stringWithFormat:@"Z : %.3f",accelData.acceleration.z];
                
    }];
}

- (void) processGyro:(CMGyroData*)gyroData withError:(NSError*)error {
    
    _rawGyroscopeString = [_rawGyroscopeString stringByAppendingFormat:@"%f,%f,%f,%f\n", gyroData.timestamp,
                               gyroData.rotationRate.x,
                               gyroData.rotationRate.y,
                               gyroData.rotationRate.z,
                               nil];
    
    [[NSOperationQueue mainQueue] addOperationWithBlock:^{
        
        //Do any updates to your label here
        self.GyroscopeX.text = [NSString stringWithFormat:@"X : %.3f",gyroData.rotationRate.x];
        self.GyroscopeY.text = [NSString stringWithFormat:@"Y : %.3f",gyroData.rotationRate.y];
        self.GyroscopeZ.text = [NSString stringWithFormat:@"Z : %.3f",gyroData.rotationRate.z];
        
    }];
}

- (void) processMotion:(CMDeviceMotion*)motion withError:(NSError*)error {
    
    
     _gravityString = [_gravityString stringByAppendingFormat:@"%f,%f,%f,%f\n", motion.timestamp,
                          motion.gravity.x,
                          motion.gravity.y,
                          motion.gravity.z,
                          nil];
    
    [[NSOperationQueue mainQueue] addOperationWithBlock:^{
        
        //Do any updates to your label here
        self.gravityX.text = [NSString stringWithFormat:@"X : %.3f",motion.gravity.x];
        self.gravityY.text = [NSString stringWithFormat:@"Y : %.3f",motion.gravity.y];
        self.gravityZ.text = [NSString stringWithFormat:@"Z : %.3f",motion.gravity.z];
    }];

}


- (void) writeDataToDisk {
    NSLog(@"Saving everything to disk!");
    
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setTimeStyle:NSDateFormatterLongStyle];
    [dateFormatter setDateStyle:NSDateFormatterShortStyle];
    
    // Some filesystems hate colons
    NSString *dateString = [[dateFormatter stringFromDate:[NSDate date]] stringByReplacingOccurrencesOfString:@":" withString:@"_"];
    // I hate spaces
    dateString = [dateString stringByReplacingOccurrencesOfString:@" " withString:@"_"];
    // Nobody can stand forward slashes
    dateString = [dateString stringByReplacingOccurrencesOfString:@"/" withString:@"_"];
    
    
    NSString *fullPath = [documentsDirectory stringByAppendingPathComponent:[NSString stringWithFormat:@"gravity_%@.txt", dateString, nil]];
        
    [_gravityString writeToFile:fullPath atomically:NO encoding:NSStringEncodingConversionAllowLossy error:nil];
        _gravityString = @"";
    
    
    fullPath = [documentsDirectory stringByAppendingPathComponent:[NSString stringWithFormat:@"rawGyroscope_%@.txt", dateString, nil]];
        
    [_rawGyroscopeString writeToFile:fullPath atomically:NO encoding:NSStringEncodingConversionAllowLossy error:nil];
    _rawGyroscopeString = @"";
    
    fullPath = [documentsDirectory stringByAppendingPathComponent:[NSString stringWithFormat:@"rawAccelerometer_%@.txt", dateString, nil]];
    [_rawAccelerometerString writeToFile:fullPath atomically:NO encoding:NSStringEncodingConversionAllowLossy error:nil];
    _rawAccelerometerString = @"";
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)ReturnButtonAction:(id)sender {
}

- (IBAction)StartButtonAction:(id)sender {
    if (currentState == 0) {
        currentState = 1;
        [_StartButton setTitle:@"STOP AND EXPORT LOG FILE" forState:UIControlStateNormal];
        [self startLoggingMotionData];
    } else if (currentState == 1) {
        currentState = 2;
        [_StartButton setTitle:@"DISPLAY LOG FILE" forState:UIControlStateNormal];
        [self stopLoggingMotionDataAndSave];
    }
}

@end
