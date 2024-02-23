# Get the full path of the current working directory
$WORKING_DIR = (Get-Location).Path

# Get the basename of the current working directory
$CURRENT_DIR = (Get-Item -Path $WORKING_DIR).Name 

$CONTAINER_RUNTIME_NAME = "$CURRENT_DIR-openjdk-17-pubsub-consumer"
$NETWORK_NAME = "frigo_network"

# Check if the network exists
$networkExists = docker network ls --filter name=$NETWORK_NAME --format '{{.Name}}' | Select-String -Pattern $NETWORK_NAME

if (-not $networkExists) {
    Write-Host "Creating network ..."
    docker network create $NETWORK_NAME
}

Write-Host $WORKING_DIR

docker run -d -it --rm `
    -v $WORKING_DIR/app:/app `
    --net $NETWORK_NAME `
    -e broker_host="broker" `
    --name $CONTAINER_RUNTIME_NAME `
    openjdk:17

    docker exec -it `
    -w /app `
    $CONTAINER_RUNTIME_NAME `
    /bin/bash -c "java -cp /app/app-jar-with-dependencies.jar main/java/Consumer_bd"
    
if ($LASTEXITCODE -ne 0) {
    docker stop $CONTAINER_RUNTIME_NAME
    exit 1
} 
    
docker stop $CONTAINER_RUNTIME_NAME
