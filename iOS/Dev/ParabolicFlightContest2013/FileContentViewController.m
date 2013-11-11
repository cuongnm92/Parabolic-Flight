//
//  FileContentViewController.m
//  ParabolicFlightContest2013
//
//  Created by cuongnm92 on 10/30/13.
//  Copyright (c) 2013 vietnam. All rights reserved.
//

#import "FileContentViewController.h"
#import "MeasureViewController.h"

@interface FileContentViewController ()

@end

@implementation FileContentViewController

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
    NSString* content = [NSString stringWithContentsOfFile:self.filePath encoding:NSUTF8StringEncoding error:NULL];
    self.TextView.text = content;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setTextView:nil];
    [super viewDidUnload];
}

- (IBAction)ReturnButtonAction:(id)sender {
    [self presentViewController:[[MeasureViewController alloc]  initWithNibName:@"MeasureViewController" bundle:nil] animated:NO completion:nil];
}
@end
