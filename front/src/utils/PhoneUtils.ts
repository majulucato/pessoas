export function isPhoneValid(phone: string): boolean {
    const phoneRegex = /^\(\d{2}\)\s\d{4,5}-\d{4}$/;
    return phoneRegex.test(phone);
}

export function formatPhone(phone: string) {
    if (phone.length === 10) {
        return phone.replace(/(\d{2})(\d{4})(\d{4})/, "($1) $2-$3");
    }
    return phone.replace(/(\d{2})(\d{5})(\d{4})/, "($1) $2-$3");
}