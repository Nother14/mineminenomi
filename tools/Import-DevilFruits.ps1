# Step 1, variable setup and loading the .env variables

#Variables
$location = Get-Location
$rootLocation = Resolve-Path (Join-Path $location '..')
$dumpLocation = "$rootLocation\tools\dump"
$location = "$rootLocation\src\main\java\xyz\pixelatedw\mineminenomi\init"

#Out Files
$outFileForDevilFruits = "$dumpLocation/DevilFruits.json"

#In Files
$inFileForDevilFruits = "$location/ModDevilFruits.java"

#Helper Functions
Set-PsEnv

# Step 2, create the dump folder if it doesn't exist

if (!(Test-Path $dumpLocation -PathType Container)) {
    New-Item -ItemType Directory -Force -Path $dumpLocation
}

# Step 3, filter the files and build a json payload

$JSON = Get-Content $inFileForDevilFruits | Select-String ', *".*"' -AllMatches | ForEach-Object {
    $_.Matches
} | ForEach-Object {
    $unlocalizedName = $_.Value.substring(2).replace('"', '')

    $df = @{
        name = $unlocalizedName
    }
    $df

} | ConvertTo-Json

# Step 4, save the file on disk as backup

$JSON | Out-File $outFileForDevilFruits -Force

# Step 5, sent a POST request towards the server

$postParams = @{ payload=$JSON }
Invoke-WebRequest -Uri http://localhost:4201/api/v1/mine-mine-no-mi/devil-fruits -ContentType "application/json" -Method POST -Body $postParams.payload