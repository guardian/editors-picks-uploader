# editors-picks-uploader
AWS Lambda to upload editors picks to the Content API.

# Overview
The editors-picks-uploader is responsible for periodically (every 5 minutes) uploading editors picks to the content API.
It does this by hitting `http://api.nextgen.guardianapps.co.uk/PATH/lite.json` where PATH is a front obtained from the 
facia client, taking the top 25 items (regardless of which collection) and sending them to be indexed by CAPI.
