FROM node:16-alpine

WORKDIR /app
COPY package*.json ./
COPY vite.config.ts ./
RUN npm install --verbose
COPY . .
CMD ["npm", "run", "test:ui"]
