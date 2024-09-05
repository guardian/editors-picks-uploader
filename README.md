# editors-picks-uploader

AWS Lambda to upload editors picks to the Content API.

## Overview

The editors-picks-uploader is responsible for periodically (every 5 minutes) uploading editors picks to the content API.
It does this by hitting `http://api.nextgen.guardianapps.co.uk/PATH/lite.json` where PATH is a front obtained from the 
facia client, taking the top 25 items (regardless of which collection) and sending them to be indexed by CAPI.

## Alarms

If editors picks data is not updating in capi then it's because either:
1. This lambda is not publishing to SNS. There is a cloudwatch alarm created by this stack for alerting when no messages are published for 10mins.
2. Porter is not reading from its SQS queue. There is a cloudwatch alarm in the Porter stack for alerting when messages on the queue are older than 20mins.

In both cases you can check the status of these metrics from the cloudwatch console.

## Dev setup

Note that this app runs on Java 11 – please set your JAVA_HOME accordingly (or use [asdf](https://asdf-vm.com/) or [sdkman](https://sdkman.io/)).
