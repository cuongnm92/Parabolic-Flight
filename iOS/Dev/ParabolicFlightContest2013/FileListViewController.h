//
//  FileListViewController.h
//  ParabolicFlightContest2013
//
//  Created by cuongnm92 on 10/30/13.
//  Copyright (c) 2013 vietnam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FileContentViewController.h"

@interface FileListViewController : UITableViewController <UITableViewDataSource> {
    NSArray *FileList;
}

@property(nonatomic, retain) FileContentViewController *fileDetail;

@end
