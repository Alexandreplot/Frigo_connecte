# Get the full path of the current working directory
$WORKING_DIR = (Get-Location).Path

# Get the basename of the current working directory
$CURRENT_DIR = (Get-Item -Path $WORKING_DIR).Name 

$NETWORK_NAME="frigo_network"

$networkExists = docker network ls | Select-String -Pattern $NETWORK_NAME

if (-not $networkExists) {
    Write-Host "Creating network ..."
    docker network create $NETWORK_NAME
}

docker run -d --rm -it `
    --name broker `
    -p 15672:15672 -p 5672:5672  `
    --net $NETWORK_NAME `
    rabbitmq:3.12.4-management-alpine
