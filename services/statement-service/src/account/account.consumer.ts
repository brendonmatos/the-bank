import { Controller } from "@nestjs/common";
import { EventPattern } from "@nestjs/microservices";

@Controller()
export class AccountConsumer {
  constructor() {
    console.log("AccountConsumer");
  }

  @EventPattern()
  async handleAccountConsumer(data: any) {
    console.log("---------->", data);
  }
}
