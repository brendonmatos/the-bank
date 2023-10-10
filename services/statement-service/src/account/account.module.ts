import { Module } from "@nestjs/common";
import { AccountResolver } from "./account.resolver";
import { AccountService } from "./account.service";
import { AccountConsumer } from "./account.consumer";

@Module({
  imports: [],
  controllers: [AccountConsumer],
  providers: [AccountResolver, AccountService],
})
export class AccountModule { }
