
#import "RNFirebasePhoneAuth.h"
//#import <Firebase/Firebase.h>


@implementation RNFirebasePhoneAuth

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

// We must explicitly expose methods otherwise JavaScript can't access anything
RCT_EXPORT_METHOD(test)
{
    //float volume = [AVAudioSession sharedInstance].outputVolume;
    NSLog(@"The system volume level is sampleeeee");
}

// We can send back a promise to our JavaScript environment :)
RCT_REMAP_METHOD(testing,
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
//    float volume = [AVAudioSession sharedInstance].outputVolume;
    NSString* volumeString = @"getttingg ";
    NSLog(@"DDDDampleeeee");

    if (volumeString) {
        resolve(volumeString);
    } else {
        reject(@"get_error", @"Error getting system volume", nil);
    }

}

RCT_EXPORT_METHOD(initFB:(NSString *)fileName
                  callback:(RCTResponseSenderBlock)successCallback
                  errorCallback:(RCTResponseSenderBlock)failureCallback) {
    NSString* volumeString = @"getttingg ";

    if (volumeString) {
        NSDictionary *resultsDict = @{
              @"success" : @" SOME DATA RETURN ",
              @"error"  : @NO
          };
        // Execute the JavaScript failure callback handler
        successCallback(@[resultsDict]);
    } else {
        NSDictionary *resultsDict = @{
          @"success" : @" SOME ERROR ",
          @"error"  : @YES
          };
        // Execute the JavaScript failure callback handler
        failureCallback(@[resultsDict]);
    }

}

@end
