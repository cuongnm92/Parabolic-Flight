//
//  FileContentViewController.h
//  ParabolicFlightContest2013
//
//  Created by cuongnm92 on 10/30/13.
//  Copyright (c) 2013 vietnam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FileContentViewController : UIViewController

- (IBAction)ReturnButtonAction:(id)sender;

@property (strong, nonatomic) IBOutlet UITextView *TextView;

@property (strong, nonatomic) NSString *filePath;

@end
