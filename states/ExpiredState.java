package states;

import models.Member;
import models.Membership;

public class ExpiredState implements MembershipState {

    @Override
    public void handleCheckIn(Member member, Membership context) {
        System.out.println("❌ ACCESS DENIED! Membership has expired!");
        member.displayInfo();
        System.out.println("⚠️ Please renew your membership to continue access.");
    }

    @Override
    public void renew(Membership context) {
        context.setState(new ActiveState());
        System.out.println("✅ Renewed successfully. Status transitioned to ACTIVE.");
    }

    @Override
    public void expire(Membership context) {
        System.out.println("ℹ️ Membership is already EXPIRED.");
    }

    @Override
    public String getStatusName() {
        return "EXPIRED";
    }
}