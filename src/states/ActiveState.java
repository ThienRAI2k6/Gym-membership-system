package states;

import models.Member;
import models.Membership;

public class ActiveState implements MembershipState {

    @Override
    public void handleCheckIn(Member member, Membership context) {
        System.out.println("✅ ACCESS GRANTED! Check-in successful!");
        member.displayInfo();
    }

    @Override
    public void renew(Membership context) {
        System.out.println("ℹ️ Membership is already ACTIVE. Renewed successfully.");
    }

    @Override
    public void expire(Membership context) {
        context.setState(new ExpiredState());
        System.out.println("⚠️ Membership has expired. Status transitioned to EXPIRED.");
    }

    @Override
    public String getStatusName() {
        return "ACTIVE";
    }
}