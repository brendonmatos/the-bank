import { Controller } from "@nestjs/common";
import { EventPattern } from "@nestjs/microservices";

@Controller()
export class MovementConsumer {
  constructor() {
    console.log("MovementConsumer 11111");
  }

  @EventPattern()
  async handleContaMovimentada(data: any) {
    console.log("---------->", data);
  }
}
