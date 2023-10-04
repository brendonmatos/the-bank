FROM oven/bun:latest

WORKDIR /app
COPY package*.json ./
COPY bun.lockb ./
RUN echo "Installing dependencies..."
RUN bun install
COPY . .

CMD ["npm", "run", "test:ui"]
