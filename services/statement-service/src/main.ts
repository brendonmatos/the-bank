import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  console.log(typeof process.env.SERVER_PORT);
  await app.listen(Number(process.env.SERVER_PORT));
}

bootstrap();
