# Get the full path of the current working directory
$WORKING_DIR = Get-Location

# Get the basename of the current working directory
$CURRENT_DIR = Split-Path -Leaf $WORKING_DIR

$CONTAINER_RUNTIME_NAME="$CURRENT_DIR-openjdk-17-pubsub-producer"

$NETWORK_NAME="frigo_network"

if (-not $networkExists) {
    Write-Host "Creating network ..."
    docker network create $NETWORK_NAME
}

docker run -d -it --rm `
    -v $WORKING_DIR/app:/app `
    --net $NETWORK_NAME `
    -e broker_host=broker `
    --name $CONTAINER_RUNTIME_NAME `
    openjdk:17


docker exec -it `
    -w /app `
    $CONTAINER_RUNTIME_NAME `
    /bin/bash -c "java -cp /app/app-jar-with-dependencies.jar main/java/Frigo_goDanseur"
if ($LASTEXITCODE -ne 0) {
    docker stop $CONTAINER_RUNTIME_NAME
    exit 1
} 

