import { Module } from "@nestjs/common";
import { MovementResolver } from "./movement.resolver";
import { MovementsService } from "./movement.service";
import { MovementConsumer } from "./movement.consumer";

@Module({
  imports: [],
  controllers: [MovementConsumer],
  providers: [MovementResolver, MovementsService],
})
export class MovementModule { }
