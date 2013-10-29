//
//  FirstViewController.m
//  ParabolicFlightContest2013
//
//  Created by cuongnm92 on 10/24/13.
//  Copyright (c) 2013 vietnam. All rights reserved.
//

#import "FirstViewController.h"

@interface FirstViewController ()

@property (strong, nonatomic) IBOutlet UILabel *teamName;

@property (strong, nonatomic) IBOutlet UIButton *startButton;

@end

@implementation FirstViewController

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
    self.view.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"para-black.png"]];
    [self.teamName setFont:[UIFont systemFontOfSize:10]];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)StartButton:(id)sender {
    if(self.measureViewController == nil) {
        self.measureViewController = [[MeasureViewController alloc]  initWithNibName:@"MeasureViewController" bundle:nil];
    }
    
    [self presentViewController:self.measureViewController animated:NO completion:nil];
}

@end
