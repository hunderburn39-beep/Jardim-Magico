shader_type canvas_item;
render_mode unshaded;
uniform sampler2D rom_palette : filter_nearest, repeat_disable;

void fragment() {
	vec4 indexed = texture(TEXTURE, UV);
	float palette_index = floor(indexed.r * 255.0 + 0.5);
	vec4 ink = texture(rom_palette, vec2((palette_index + 0.5) / 256.0, 0.5));
	float material_byte = floor(indexed.g * 255.0 + 0.5);
	vec3 art = ink.rgb;
	if (material_byte > 254.5) {
		float steel = indexed.r;
		art = min(vec3(1.0), vec3(0.94, 0.97, 1.0) * steel + vec3(0.015));
	} else if (material_byte > 0.0) {
		float tone = (material_byte - 64.0) / 63.0;
		art = tone < 0.0 ? mix(art, vec3(0.0), -tone) : mix(art, vec3(1.0), tone);
	}
	COLOR = vec4(art, indexed.a);
}
